package be.betty.gwtp.server;

//import javax.servlet.http.HttpServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class FileUpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "/tmp/";
	private HashMap<String, String> project_attributes;
	
	@Inject FileUpServlet() {
		project_attributes = new HashMap<String, String>(8, (float) 0.5);
	}
	
	 @Override public void doGet(
	          HttpServletRequest req,  HttpServletResponse resp)
	        throws ServletException, IOException {
	             System.out.println("Do get !!!!");
	       }
	 
	 @Override public void doPost(
	          HttpServletRequest req,  HttpServletResponse resp)
	        throws ServletException, IOException {
		 		
	             System.out.println("Do POST ");
	            
	             //TODO: check user credentials !!
	             
	          // process only multipart requests
	     		if (ServletFileUpload.isMultipartContent(req)) {
	     			//System.out.println("is multipart");
	     		
	     			// Create a factory for disk-based file items
	     			FileItemFactory factory = new DiskFileItemFactory();

	     			// Create a new file upload handler
	     			ServletFileUpload upload = new ServletFileUpload(factory);

	     			// Parse the request
	     			try {
	     				List<FileItem> items = upload.parseRequest(req);
	     			
	     				for (FileItem fileItem : items) {
	     					
	     					if (fileItem.isFormField())
	     						project_attributes.put(fileItem.getFieldName(), fileItem.getString());
	     					
	     					else processFile(fileItem, resp);
	     					
	     				}
	     			} catch (Exception e) {
	     				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
	     						"An error occurred while creating the file : " + e.getMessage());
	     			}

	     		} else {
	     			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
	     							"Request contents type is not supported by the servlet.");
	     		}
	     		
	     		saveProjectInBdd();
	       }


	private void saveProjectInBdd() {

		//TODO: meilleur moyen de retrouver le lastId (sans avoir des problem de concurence)--> Hibernate ?
		if (!project_attributes.containsKey("file_courses"))
			project_attributes.put("file_courses", "empty_file"+System.currentTimeMillis());
		// TODO: gestion d'ereurs !!
		SQLHandler sqlHandler = new SQLHandler();
		if (!sqlHandler.exexuteUpdate("insert into project (name, file_activities) " +
				"values ('"+project_attributes.get("project_name")+"', '"+project_attributes.get("file_courses")+"' )"))
			System.out.println("creation d'un nouvo projet echoue.. Il faut absolument gerer cette erreur autrement");
		
		String id = sqlHandler.executeSingleQuery("SELECT id from project " +
				"where file_activities = '"+project_attributes.get("file_courses")+"'","id");
		System.out.println("last id = " +id);
		if (!sqlHandler.exexuteUpdate("insert into user_project (user_id, project_id) values ('1', '"+id+"') "))
			System.out.println("creation d'un nouvo projet echoue.. Il faut absolument gerer cette erreur autrement");
		
	}

	private void processFile(FileItem fileItem, HttpServletResponse resp) throws IOException, Exception {
			String fileName = fileItem.getName()+System.currentTimeMillis();
			// get only the file name not whole path
			if (fileName != null) {
		        fileName = FilenameUtils. getName(fileName);
		    }

			File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
			if (uploadedFile.createNewFile()) {
				fileItem.write(uploadedFile);
//				resp.setStatus(HttpServletResponse.SC_CREATED);
//				resp.getWriter().print("The file was created successfully.");
//				resp.flushBuffer();
				project_attributes.put(fileItem.getFieldName(), fileName);
			} else
				throw new IOException("The file already exists in repository.");
		
	}


}
