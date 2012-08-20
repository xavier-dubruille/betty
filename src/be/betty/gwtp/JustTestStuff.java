package be.betty.gwtp;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.betty.gwtp.server.CreateUserProject;
import be.betty.gwtp.server.HibernateUtils;
import be.betty.gwtp.server.NoFileException;
import be.betty.gwtp.server.bdd.ProjectInstance;
import be.betty.gwtp.server.bdd.Project_entity;

public class JustTestStuff {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws NoFileException 
	 */
	public static void main(String[] args) throws NoFileException, FileNotFoundException, IOException {
		Project_entity p= new Project_entity();
		
		p.setRoom_file("/Users/Poulpix/tfe/DefinitionLocauxLLN_jack.xls");
		p.setCourse_file("/tmp/l.xls");
		p.setName("Nice small2");
		
		
		CreateUserProject c = new CreateUserProject(p);
		c.createStateFromRoomFile();
		c.createStateFromCardFile();
		c.setRoomToCourse();
	
		c.saveStateToBdd();
	}

}
