import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

//import jotov.versia.pojo.Actions;
//import jotov.versia.pojo.Permitions;
import jotov.versia.pojo.Product;
import jotov.versia.pojo.User;
import jotov.versia.pojo.Workspace;
//import jotov.versia.pojo.User;
import jotov.versia.pojo.Release;

public class Main {
	//static EntityManager em;

	@SuppressWarnings("unchecked")
	public static final void main(String[] args) {

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("versia_er3");
		try {
			EntityManager em = factory.createEntityManager();
			//em.getTransaction().begin();
			//em.find(Workspace.class, arg1);
			Query query = em.createQuery("select a from Workspace a where a.workspaceId = 3");
			for (Workspace a : (List<Workspace>) query.getResultList()) {
				System.out.println(a);
				printWorkspace(a);
			}
			//em.flush();
			//em.getTransaction().commit();

			em.close();

		} finally {
			factory.close();
		}
	}

//	private static void printReleases(Product a) {
//
//		for (Release r : (List<Release>) a.getRealeses()) {
//			// em.refresh(r);
//			System.out.println(r);
//			printWorkspace(r.getMasterWorkspace());
//		}
//
//	}

	private static void printWorkspace(Workspace workspace) {
		System.out.println(workspace);
		User u = workspace.getUser();
		if(u!=null)
			System.out.println(u);
		else
			System.out.println("\t Not opened workspace");
		for (Workspace w:(List<Workspace>) workspace.getOffspringWorkspaces()){
			printWorkspace(w);
		}
		
	}
}