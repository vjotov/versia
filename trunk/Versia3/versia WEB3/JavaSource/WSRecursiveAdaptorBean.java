

import com.jotov.versia.beans.UserSessionBean;

public class WSRecursiveAdaptorBean {
	private UserSessionBean session;
	private WSNode[] WSRoots;

	public synchronized WSNode[] getWSRoots() {
		WSRoots = new WSNode[1];
		WSRoots[0] = new WSNode(session.getRelease().getMasterWorkspace());
		return WSRoots;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}
}
