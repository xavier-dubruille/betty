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
import org.apache.log4j.Logger;

import be.betty.gwtp.client.model.Project;

import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class FileUpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "/tmp/";
	private static final Logger logger = Logger.getLogger(FileUpServlet.class);

	
	@Inject FileUpServlet() {
		
	}
	
	 @Override public void doGet(
	          HttpServletRequest req,  HttpServletResponse resp)
	        throws ServletException, IOException {
		 		logger.trace("A post request have been made");
	             System.out.println("Do get !!!!");
	       }
	 
	 @Override public void doPost(
	          HttpServletRequest req,  HttpServletResponse resp)
	        throws ServletException, IOException {
		 logger.trace("A post request have been made");
		 	HashMap<String, String>  project_attributes = new HashMap<String, String>(8, (float) 0.5);
		 	//TODO : We should definity use model.project instead of this hashMap !!
		 		
	             System.out.println("Do POST ");
	            
	             //TODO: check user credentials !! (avec un cookie ou avec un champ caché de le formulaire ?)
	             //TODO: que faire si ce n'est pas une multipart requests ?  ca pourrait arriver ?
	          // process only multipart requests
	     		if (ServletFileUpload.isMultipartContent(req)) {
	     			//System.out.println("is multipart");
	     		
	     			Project projectToBeSaved = new Project();
	     			// Create a factory for disk-based file items
	     			FileItemFactory factory = new DiskFileItemFactory();

	     			// Create a new file upload handler
	     			ServletFileUpload upload = new ServletFileUpload(factory);

	     			// Parse the request
	     			try {
	     				List<FileItem> items = upload.parseRequest(req);
	     			
	     				for (FileItem fileItem : items) {
	     					logger.trace("precess item: "+fileItem.getFieldName());
	     					
	     					if (fileItem.isFormField())
	     						project_attributes.put(fileItem.getFieldName(), fileItem.getString());
	     					
	     					else processFile(fileItem, resp, project_attributes);
	     					
	     				}
	     			} catch (Exception e) {
	     				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
	     						"An error occurred while creating the file : " + e.getMessage());
	     			}

	     		} else {
	     			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
	     							"Request contents type is not supported by the servlet.");
	     		}
	     		
	     		saveProjectInBdd(project_attributes);
	       }


	private void saveProjectInBdd(HashMap<String, String>  project_attributes) {

		//logger.trace("");
		//TODO: meilleur moyen de retrouver le lastId (sans avoir des problem de concurence)--> Hibernate ?
		long timeMilli =  System.currentTimeMillis();
		if (!project_attributes.containsKey("file_courses"))
			project_attributes.put("file_courses", "empty_"+ timeMilli);
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

	private void processFile(FileItem fileItem, HttpServletResponse resp, HashMap<String, String>  project_attributes ) throws IOException, Exception {
		
		//TODO: est ce que le fileName peut être null, et que faire dans ce cas ?
			String fileName = fileItem.getName()+System.currentTimeMillis();
			// get only the file name not whole path
//			if (fileName != null) {
//		        fileName = FilenameUtils.getName(fileName);
//		    }
			
			logger.trace("about to save file named "+fileName);
			File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
			if (uploadedFile.createNewFile()) {
				fileItem.write(uploadedFile);
//				resp.setStatus(HttpServletResponse.SC_CREATED);
//				resp.getWriter().print("The file was created successfully.");
//				resp.flushBuffer();
				project_attributes.put(fileItem.getFieldName(), fileName);
			} else
			{
				logger.error("io excetion when creating the file");
				throw new IOException("The file already exists in repository.");
			
			}
		
	}


}
