import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jotov.versia.beans.vobj.VisibileItemsExtractor;
import com.jotov.versia.beans.vobj.VisibleItems;
import com.jotov.versia.orm.Cause;
import com.jotov.versia.orm.Product;
import com.jotov.versia.orm.Release;
import com.jotov.versia.orm.UserProfile;
import com.jotov.versia.orm.Actions;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;
import com.jotov.versia.orm.WSpace;
import com.jotov.versia.orm.WorkItemAttachement;

public class Main {
	// static EntityManager em;

	@SuppressWarnings("unchecked")
	public static final void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("versia_er3");
		try {
			System.out.println("InTry");
			EntityManager em = factory.createEntityManager();
			// em.getTransaction().begin();
			//
			System.out.println("Before Query");
			// Product prod = loadProduct(em);
			// em.getTransaction().begin();

			// VersionArc va = em.find(VersionArc.class, 122);
			WSpace ws = em.find(WSpace.class, 151);
			VObjectVersion localVOV = em.find(VObjectVersion.class, 24);
			UserProfile user = em.find(UserProfile.class, 1);
			WSpace aws = ws.getAncestorWorkspace();
			if (Object.class.isInstance(aws)
					&& ws.getLocalVersions().contains(localVOV)) {
				VObjectVersion ancestorVOV = localVOV.getAncestorVersion();
				if (Object.class.isInstance(ancestorVOV)) {
					em.getTransaction().begin();
					ancestorVOV.setWorkspace(null);
					aws.removeLocalVersion(ancestorVOV);

					localVOV.setWorkspace(aws);
					localVOV.addPrecetorsArc(VersionArc.createArcs(localVOV,
							ancestorVOV, aws, user));
					ws.removeLocalVersion(localVOV);
					aws.addLocalVersion(localVOV);
					em.getTransaction().commit();
				} else {
					em.getTransaction().begin();
					localVOV.setWorkspace(aws);
					ws.removeLocalVersion(localVOV);
					aws.addLocalVersion(localVOV);
					em.getTransaction().commit();
				}

			}

			// VObject vo = em.find(VObject.class, 1);
			// WSpace ws = em.find(WSpace.class, 152);
			// WorkItemAttachement wia = WorkItemAttachement
			// .createWorkItemAttachement(ws, vo);
			// //em.persist(wia);
			// em.persist(ws);

			// ArrayList<VisibleItems> vi =
			// VisibileItemsExtractor.buildVersions(ws);

			// em.flush();
			// em.getTransaction().commit();
			System.out.println("After Query");

			em.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			System.out.println("Finally");
			factory.close();
		}
	}

	private static Product loadProduct(EntityManager em) {
		// TODO Auto-generated method stub
		Query query = em
				.createQuery("select p from Product p WHERE p.productId = 51 ");
		Product a = (Product) query.getSingleResult();
		return a;
	}

	// private static void printReleases(Product a) {
	//
	// for (Release r : (List<Release>) a.getRealeses()) {
	// // em.refresh(r);
	// System.out.println(r);
	// printWorkspace(r.getMasterWorkspace());
	// }
	//
	// }

	/*
	 * private static void printWorkspace(WSpace workspace) {
	 * System.out.println(workspace); UserProfile u = workspace.getUser();
	 * if(u!=null) System.out.println(u); else
	 * System.out.println("\t Not opened workspace"); for (WSpace
	 * w:(List<WSpace>) workspace.getOffspringWorkspaces()){ printWorkspace(w);
	 * }
	 * 
	 * }
	 */
}
// for(VisibleItems v:vi){
// System.out.println(v.getVo().getVObjectId()+"/"+v.getVov().getGlobalVPId()+v.getVov().getObjectName()+"#"+v.getVvector());
// }
// Query query = em
// .createQuery("select u from UserProfile u where u.userId=:id");
// query.setParameter("id", 1);
// for (UserProfile a : (List<UserProfile>) query.getResultList()) {
// System.out.println(a.getUserId() + "-" + a.getUserName());
// }

// UserProfile user = (UserProfile) query.getSingleResult();

// Query query =
// em.createQuery("select r from Release r where r.product=:prod");
// query.setParameter("prod", prod);
// for (Release a : (List<Release>) query.getResultList()) {
// System.out.println(a.getReleaseId()+"-"+a.getReleaseName());
// }
// List<Release> result = query.getResultList();
// ArrayList<Actions> result = (ArrayList<Actions>)
// query.getResultList();
// Query query =
// em.createQuery("select up from UserProfile up where up.userName = 'admin' and up.password = 'admin'");
// for (UserProfile a : (List<UserProfile>) query.getResultList()) {
// System.out.println(a.getUserName());
// }