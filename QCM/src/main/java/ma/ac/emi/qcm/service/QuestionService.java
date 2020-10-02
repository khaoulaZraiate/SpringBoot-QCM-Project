package ma.ac.emi.qcm.service;

import java.lang.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import ma.ac.emi.qcm.controller.ClasseController;
import ma.ac.emi.qcm.entities.*;
import ma.ac.emi.qcm.repository.QuestionRepository;
import ma.ac.emi.qcm.repository.ReponseRepository;
import ma.ac.emi.qcm.repository.ThemeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class QuestionService {
	
	@Autowired
	ThemeRepository themeRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	ReponseRepository reponseRepository;
	
	
	public List<Question> generateQuestionString(Formateur formateur ,Matiere matiere) {
		
		List<Question> questions= new ArrayList<>();

		Theme theme=new Theme("Programmation",matiere);
		themeRepository.save(theme);
		
		String[] quests = {"Cochez les methodes qui appartiennent a la classe ",
				"Selectionnez les interfaces de la classe ",
				"Selectionnez les constructeurs de la classe "};
		QuestionType[] types = {QuestionType.CLASS,QuestionType.INTERFACE,QuestionType.CONSTRUCTOR};
		int i=0;
		for(String ques: quests) {
			DemoClass demo = new DemoClass(types[i]);
			String classeChoisis = demo.getRandomClass();
			Question question=new Question(ques+ classeChoisis + " :" ,
					2, Difficulte.Simple, true, false, theme, formateur);
			questionRepository.save(question);
			
			List<Reponse> reps = new ArrayList<>();
			try {
				
				reps = demo.getReponse(types[i++], classeChoisis, question);
				Method[] methodes=demo.getClass().getClass().getDeclaredMethods();
				Reponse reponseFausse1 =new Reponse(methodes[new Random().nextInt(methodes.length)].getName().toString(), false, question);
				reponseRepository.save(reponseFausse1);
				reps.add(reponseFausse1);
				Reponse reponseFausse2 =new Reponse(methodes[new Random().nextInt(methodes.length)].getName().toUpperCase().toString(), false, question);
				reponseRepository.save(reponseFausse2);
				reps.add(reponseFausse2);
				
				for(Reponse rep: reps)
					question.getReponses().add(rep);
			} catch (ClassNotFoundException e1) {}
			questions.add(question);
			
		}
	
		
		return questions;
		
	}


}
