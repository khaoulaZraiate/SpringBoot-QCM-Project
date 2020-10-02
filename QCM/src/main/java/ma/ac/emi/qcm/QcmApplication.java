package ma.ac.emi.qcm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ma.ac.emi.qcm.entities.*;
import ma.ac.emi.qcm.repository.*;

@SpringBootApplication
public class QcmApplication {

	@Autowired
	ThemeRepository themeRepo;
	@Autowired
	FormationRepository formationRepo;
	@Autowired
	NiveauRepository niveauRepo;
	@Autowired
	MatiereRepository matiereRepo;
	@Autowired
	FormateurRepository formateurRepository;
	@Autowired
	QCMRepository qcmRepository;
	@Autowired
	ClasseRepository classeRepo;
	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	ReponseRepository reponseRepository;
	


	public static void main(String[] args) {
		SpringApplication.run(QcmApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(EleveRepository er) {
		return args -> {
			Formateur formateur = new Formateur("ali", "alimalaoui3@gmail.com", "ali", "1234");
			formateurRepository.save(formateur);
			Formateur formateur1 = new Formateur("Oussama", "oussma@gmail.com", "oussama", "1234");
			formateurRepository.save(formateur1);

			Formation formation = new Formation("ingénieurs");
			formationRepo.save(formation);
			Formation formation1 = new Formation("Doctorale");
			formationRepo.save(formation1);

			Niveau niveau0 = new Niveau("1ere annee", formation1);
			niveauRepo.save(niveau0);
			Niveau niveau1 = new Niveau("1ere annee", formation);
			niveauRepo.save(niveau1);
			Niveau niveau2 = new Niveau("2eme annee", formation);
			niveauRepo.save(niveau2);
			Niveau niveau3 = new Niveau("3eme annee", formation);
			niveauRepo.save(niveau3);

			Matiere matiere = new Matiere("JEE", niveau2);
			matiereRepo.save(matiere);
			Matiere matiere2 = new Matiere("Design Pattern", niveau2);
			matiereRepo.save(matiere2);

			Theme theme = new Theme("Culture", matiere);
			themeRepo.save(theme);
			Theme theme1 = new Theme("Culture", matiere2);
			themeRepo.save(theme1);

			Classe classe = new Classe("G INF", niveau2);
			classeRepo.save(classe);
			

	

//
		Question question1 = new Question("Definition de ORM", 3, Difficulte.Normale, false, false, theme,
					formateur);
			Question question2 = new Question("Definition d'un EntityManager", 3, Difficulte.Difficile, false, false,
					theme, formateur);
			Question question3 = new Question("Definir Composite", 3, Difficulte.Normale, false, false, theme1,
					formateur1);



			questionRepo.save(question1);
			questionRepo.save(question2);
			questionRepo.save(question3);

	


			reponseRepository.save(new Reponse("The first question", true, question1));
			reponseRepository.save(new Reponse("The second question", false, question1));
			reponseRepository.save(new Reponse("The third question", true, question1));
			
			

			
			
			
			Classe classe1 = new Classe("G INF", niveau1);
			classeRepo.save(classe1);
			Formateur formateurK = new Formateur("Kabbaj", "Kabbaj@gmail.com", "kabbaj", "1234");
			formateurRepository.save(formateurK);
			Matiere matiereJava = new Matiere("JAVA", niveau1);
			matiereRepo.save(matiereJava);
			QCM qcmJava = new QCM("Test JAVA", Type.Normale, Mode.MultiChoix, true, false, classe1, formateurK, matiereJava);
			qcmRepository.save(qcmJava);

			

			      
		};
	}

}
