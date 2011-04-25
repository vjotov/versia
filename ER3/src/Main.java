import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jotov.versia.orml.Product;
import com.jotov.versia.orml.Release;
import com.jotov.versia.orml.UserProfile;
import com.jotov.versia.orml.WSpace;

//import jotov.versia.pojo.Actions;
//import jotov.versia.pojo.Permitions;
//import jotov.versia.pojo.User;

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
			Query query = em.createQuery("select a from WSpace a where a.wSpaceId = 3");
			for (WSpace a : (List<WSpace>) query.getResultList()) {
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

	private static void printWorkspace(WSpace workspace) {
		System.out.println(workspace);
		UserProfile u = workspace.getUser();
		if(u!=null)
			System.out.println(u);
		else
			System.out.println("\t Not opened workspace");
		for (WSpace w:(List<WSpace>) workspace.getOffspringWorkspaces()){
			printWorkspace(w);
		}
		
	}
}
