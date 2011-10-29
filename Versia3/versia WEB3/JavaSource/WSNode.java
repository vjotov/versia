

import java.util.List;
import com.jotov.versia.orm.Release;
import com.jotov.versia.orm.WSpace;

public class WSNode {
	private WSNode[] children;
	private WSpace currentNode;

	public WSNode() {
	}

	public WSNode(WSpace currentNode) {
		this.currentNode = currentNode;
	}

	public WSNode(Release release) {
		this.currentNode = release.getMasterWorkspace();
	}

	public WSNode[] getNodes() {
		// TODO Auto-generated method stub
		if (children == null) {
			List<WSpace> chld = currentNode.getOffspringWorkspaces();
			if (chld != null) {
				Object[] nodes = (Object[]) chld.toArray();
				children = new WSNode[nodes.length];
				for (int i = 0; i < nodes.length; i++) {
					children[i] = new WSNode((WSpace) nodes[i]);
				}
			}
		}
		return null;
	}

	public int getNodeId() {
		return currentNode.getWSpaceId();
	}

	public String getNodeName() {
		return currentNode.getWSpaceName();
	}

	@Override
	public String toString() {
		return currentNode.getWSpaceId() + "#" + currentNode.getWSpaceName();
	}

}
