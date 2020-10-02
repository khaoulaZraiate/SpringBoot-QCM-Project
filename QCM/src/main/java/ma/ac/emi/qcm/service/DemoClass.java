package ma.ac.emi.qcm.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ma.ac.emi.qcm.entities.Question;
import ma.ac.emi.qcm.entities.Reponse;

public class DemoClass {
	
	private Integer integer;
	private String string;
	private BigDecimal bigDec;
	private Boolean bool;
	private List<String> list;
	
	public DemoClass(QuestionType type) {
		this.list=new ArrayList<>();
		for(Field f:this.getClass().getDeclaredFields()) {
			String cls = f.toString().split(" ")[1];
			try {
			if(!cls.contains("static") && this.getRandomMethods(cls, type).size() > 0)
				list.add(f.toString().split(" ")[1]);
			}catch(Exception e) {}
		}
	}
	
	
	public List<String> getList(){
		return this.list;
	}
	
	public String getRandomClass() {
		return this.list.get(new Random().nextInt(this.list.size())).toString();
				
	}
	
	public List<String> getRandomMethods(String classeString,QuestionType type) throws ClassNotFoundException{
		Class classe = Class.forName(classeString);//this.getRandomClass()
		List<String> methodesString=new ArrayList<>();
		switch(type) {
		case INTERFACE:
			Class[] meths0 = classe.getInterfaces();
			for(Class m:meths0) {
				methodesString.add(m.toString());
			}
			break;
		case CONSTRUCTOR:
			Constructor[] meths = classe.getDeclaredConstructors();
			for(Constructor m:meths) {
				methodesString.add(m.getName().toString());
			}
			break;
		case CLASS:
			Method[] meths4 = classe.getDeclaredMethods();
			for(Method m:meths4) {
				methodesString.add(m.getName().toString());
			}
			break;
		}
		
		return methodesString;
	}
	
	
	public List<Reponse> getReponse(QuestionType type,String classeChoisis,Question question) throws ClassNotFoundException {
		List<Reponse> reps = new ArrayList<>(); 
		List<String> methodes = new DemoClass(type)
				.getRandomMethods(new DemoClass(type).getRandomClass(),type);
		while(methodes.size() <=1)
			methodes = new DemoClass(type)
			.getRandomMethods(new DemoClass(type).getRandomClass(),type);
		
		List<String> vraiMethodes = this.getRandomMethods(classeChoisis,type);
		
		
		Class classe = Class.forName(classeChoisis);
		
		int QSTNUMBER =(methodes.size() > 3)? 3: methodes.size();
		if(QSTNUMBER > 3)
			methodes.set(new Random().nextInt(QSTNUMBER), vraiMethodes.get(new Random().nextInt(vraiMethodes.size())));
		else if(vraiMethodes.size() > 0)
			methodes.add(vraiMethodes.get(new Random().nextInt(vraiMethodes.size())));
		
		for (int i=0;i<QSTNUMBER;i++) {
			String meth=methodes.get(i);
			boolean rep=true;
			try {
				if(type == QuestionType.CLASS || type == QuestionType.INTERFACE)
					classe.getClass().getMethod(meth);
				else
					classe.getClass().getConstructors().toString().contains(meth);
			} catch (Exception e) {
				rep=false;
			}
			Reponse reponse =new Reponse(meth,rep,question);
			reps.add(reponse);
		}
		return reps;
	}
	
	

}
