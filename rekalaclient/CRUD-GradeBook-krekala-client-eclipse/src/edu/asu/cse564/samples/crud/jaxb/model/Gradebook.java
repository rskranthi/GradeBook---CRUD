/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.samples.crud.jaxb.model;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  
public class Gradebook {
    
   HashMap<Integer,Student> students;

    public HashMap<Integer, Student> getStudents() {
	return students;
}

    public void setStudents(HashMap<Integer, Student> students) {
	this.students = students;
}

	public Gradebook() {
        students = new HashMap<Integer,Student>();
    }

    
}
