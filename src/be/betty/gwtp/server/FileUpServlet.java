package be.betty.gwtp.server;

//import javax.servlet.http.HttpServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.client.model.Project;
import be.betty.gwtp.server.bdd.Project_entity;
import be.betty.gwtp.server.bdd.Session_id;
import be.betty.gwtp.server.bdd.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class FileUpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "/tmp/"; //TODO: est-ce une bonne idée de le mettre ds le tmp ?
	private static final Logger logger = Logger.getLogger(FileUpServlet.class);



	@Inject FileUpServlet() {

	}

	@Override public void doGet(
			HttpServletRequest req,  HttpServletResponse resp)
					throws ServletException, IOException {
		logger.trace("A GET request have been made");
		System.out.println("Do get !!!!");
	}

	@Override public void doPost(
			HttpServletRequest req,  HttpServletResponse resp)
					throws ServletException, IOException {
		System.out.println("do POST!");
		logger.trace("A post request have been made");
		HashMap<String, String>  project_attributes = new HashMap<String, String>(8, (float) 0.5);
		//TODO : We should definity use model.project instead of this hashMap !! (or index.java ?)

	
		//TODO: check user credentials !! (avec un cookie ou avec un champ caché de le formulaire ?)
		//TODO: que faire si ce n'est pas une multipart requests ?  ca pourrait arriver ?
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
			logger.error("The post request is not multipart !!");
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Request contents type is not supported by the servlet.");
		}

		saveProjectInBdd(project_attributes);
	}


	/**
	 * @param projectToBeSaved 
	 * 
	 */
	private void saveProjectInBdd(HashMap<String, String>  project_attributes) {

		logger.trace("savin' project in bdd. projectName=" + project_attributes.get("name") + ", and projectFile="+ project_attributes.get("file_courses"));


		



		// etc
		
		Session s = HibernateUtils.getSession();
		Transaction t = s.beginTransaction();
		//System.out.println("sess_id = "+project_attributes.get("sess_id"));
		
		
		Session_id sess_id = (Session_id) s.get(Session_id.class, project_attributes.get("sess_id"));
		
		if (sess_id == null) return; //TODO: Faut une gestion d'erreur !  Mauvais Sess_id !
		//TODO: Faut verifier ici si le sess_id est tjs valide!
		
		User user = sess_id.getUser_id();
		//System.out.println("user======> "+user.getName());
		
		Project_entity projectToBeSaved = new Project_entity();
		projectToBeSaved.setCourse_file(UPLOAD_DIRECTORY+project_attributes.get("file_courses"));
		projectToBeSaved.setName(project_attributes.get("project_name"));
		
		//s.update(user);
		System.out.println("****zero****");
		s.save(projectToBeSaved);
		System.out.println("****un****");
		projectToBeSaved.getUsers().add(user);
		System.out.println("****deux****");
		user.getProjects().add(projectToBeSaved);
		
		s.save(projectToBeSaved);
		s.save(user);
		
		t.commit();
		s.close();


		CreateUserProject create = new CreateUserProject(projectToBeSaved);

		try {
			create.createStateFromCardFile();
		} catch (NoFileException e) {
			logger.error("problems with files, project can't be created 1 ->"+e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.error("problems with files, project can't be created 2 ->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("problems with files, project can't be created 3 ->"+e.getMessage());
			e.printStackTrace();
		}
		// TODO: gestion d'ereurs..  (et prevenir l'utilisateur de l'erreure..)


	}

	/*
	 * This method is just supposed to write the file on the disk
	 */
	private void processFile(FileItem fileItem, HttpServletResponse resp, HashMap<String, String>  project_attributes ) throws IOException, Exception {

		logger.trace("about to process file named: "+fileItem.getName());
		//TODO: est ce que le fileName peut être null, et que faire dans ce cas ?
		// faudrait aussi prendre que les x premier char pour eviter des exeption de longueur 
		String fileName = "A_"+System.currentTimeMillis()+fileItem.getName();
		// get only the file name not whole path
		//			if (fileName != null) {
		//		        fileName = Filename Utils.getName(fileName);
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
