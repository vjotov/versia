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
import com.jotov.versia.orm.VComposer;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;
import com.jotov.versia.orm.WSpace;
import com.jotov.versia.orm.WorkItemAttachement;

public class Main {
	//static EntityManager em;

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
			

			// VersionArc va = em.find(VersionArc.class, 122);
			
			WSpace ws = em.find(WSpace.class, 152);
//			VObjectVersion superVOV = em.find(VObjectVersion.class, 9);
//			UserProfile user = em.find(UserProfile.class, 1);
			ArrayList<VisibleItems> a = VisibileItemsExtractor.buildVersions(ws);
			em.getTransaction().begin();
//			publishVersion(superVOV,ws, ws.getAncestorWorkspace(),user, em);
			em.getTransaction().commit();
			
			System.out.println("After Query");

			em.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			System.out.println("Finally");
			factory.close();
		}
	}

	@SuppressWarnings("unchecked")
	private static void publishVersion(VObjectVersion pVOV, WSpace ws,
			WSpace ancestorWS, UserProfile user, EntityManager em) {
		// Separate method for recursive publication of sub-objects of a
		// composed object
		
		if (!pVOV.getWorkspace().equals(ws))
			// not local object => nothing for publication
			return;
		
		VObjectVersion ancestorVOV = pVOV.getAncestorVersion();
		if (Object.class.isInstance(ancestorVOV)) {

			ancestorVOV.setWorkspace(null);
			ancestorWS.removeLocalVersion(ancestorVOV);

			pVOV.setWorkspace(ancestorWS);
			pVOV.addPrecetorsArc(VersionArc.createArcs(pVOV, ancestorVOV,
					ancestorWS, user));
			ws.removeLocalVersion(pVOV);
			ancestorWS.addLocalVersion(pVOV);

		} else {
			pVOV.setWorkspace(ancestorWS);
			ws.removeLocalVersion(pVOV);
			ancestorWS.addLocalVersion(pVOV);
		}

		Query query = em
				.createQuery("SELECT c FROM VComposer c WHERE c.superObject = :super");
		query.setParameter("super", pVOV);
		List<VComposer> vcs = query.getResultList();
		for (VComposer vc : vcs) {
			publishVersion(vc.getSubObject(), ws, ancestorWS, user, em);
		}
	}
}