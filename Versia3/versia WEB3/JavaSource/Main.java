import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

			// Query q =
			// em.createQuery("SELECT w FROM WSpace w ORDER BY w.rv DESC");
			// WSpace w = (WSpace) q.getResultList().get(0);

			// VersionArc va = em.find(VersionArc.class, 122);

			WSpace ws = em.find(WSpace.class, 152);
			VObject vObject = em.find(VObject.class, 5);
			// // VObjectVersion superVOV = em.find(VObjectVersion.class, 9);
			// // UserProfile user = em.find(UserProfile.class, 1);
			// ArrayList<VisibleItems> a =
			// VisibileItemsExtractor.buildVersions(ws);
			em.getTransaction().begin();
			// Query q =
			// em.createQuery("SELECT COUNT(w) FROM WorkItemAttachement w WHERE w.workitem = :obj AND w.workspace = :wspace");

			Query q = em
					.createQuery("SELECT v FROM VObjectVersion v WHERE v.workspace = :wsParameter AND v.vobject = :objParameter");
			q.setParameter("wsParameter", ws);
			q.setParameter("objParameter", 	vObject);
			// TODO Auto-generated method stub
			List<VObjectVersion> ancestorVOVs = (List<VObjectVersion>) q
					.getResultList();
			VObjectVersion a;
			if (ancestorVOVs.size() > 0) {
				a = ancestorVOVs.get(0);
			}
//			em.persist(obj);

			// Query q1 =
			// em.createQuery("UPDATE WSpace w SET w.lv = w.lv+2 WHERE w.lv >= :lv_parameter");
			// Query q2 =
			// em.createQuery("UPDATE WSpace w SET w.rv = w.rv+2 WHERE w.rv >= :lv_parameter");
			//
			// q1.setParameter("lv_parameter", lv);
			// q2.setParameter("lv_parameter", lv);
			// int updated = q1.executeUpdate();
			// System.out.println("1 Updated "+updated+" LV values of WSpace objects");
			// updated = q2.executeUpdate();
			// System.out.println("2 Updated "+updated+" RV values of WSpace objects");

			// int lv = ws.getLv();
			// Query q1 =
			// em.createQuery("UPDATE WSpace w SET w.lv = w.lv-2 WHERE w.lv >= :lv_parameter");
			// Query q2 =
			// em.createQuery("UPDATE WSpace w SET w.rv = w.rv-2 WHERE w.rv >= :lv_parameter");
			// q1.setParameter("lv_parameter", lv);
			// q2.setParameter("lv_parameter", lv);
			// int updated = q1.executeUpdate();
			// System.out.println("1) Updated "+updated+" LV values of WSpace objects");
			// updated = q2.executeUpdate();
			// System.out.println("2) Updated "+updated+" RV values of WSpace objects");

			em.getTransaction().commit();
			// em.getTransaction().rollback();

			System.out.println("After Query");

			em.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			System.out.println("Finally");
			factory.close();
		}
	}
	//
	// @SuppressWarnings("unchecked")
	// private static void publishVersion(VObjectVersion pVOV, WSpace ws,
	// WSpace ancestorWS, UserProfile user, EntityManager em) {
	// // Separate method for recursive publication of sub-objects of a
	// // composed object
	//
	// if (!pVOV.getWorkspace().equals(ws))
	// // not local object => nothing for publication
	// return;
	//
	// VObjectVersion ancestorVOV = pVOV.getAncestorVersion();
	// if (Object.class.isInstance(ancestorVOV)) {
	//
	// ancestorVOV.setWorkspace(null);
	// ancestorWS.removeLocalVersion(ancestorVOV);
	//
	// pVOV.setWorkspace(ancestorWS);
	// pVOV.addPrecetorsArc(VersionArc.createArcs(pVOV, ancestorVOV,
	// ancestorWS, user));
	// ws.removeLocalVersion(pVOV);
	// ancestorWS.addLocalVersion(pVOV);
	//
	// } else {
	// pVOV.setWorkspace(ancestorWS);
	// ws.removeLocalVersion(pVOV);
	// ancestorWS.addLocalVersion(pVOV);
	// }
	//
	// Query query = em
	// .createQuery("SELECT c FROM VComposer c WHERE c.superObject = :super");
	// query.setParameter("super", pVOV);
	// List<VComposer> vcs = query.getResultList();
	// for (VComposer vc : vcs) {
	// publishVersion(vc.getSubObject(), ws, ancestorWS, user, em);
	// }
	// }
}