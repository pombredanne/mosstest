package net.mosstest.servercore;

import java.util.ArrayList;
import java.util.HashMap;

import net.mosstest.scripting.MapNode;

import org.iq80.leveldb.DB;

import com.google.common.collect.HashBiMap;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractNodeManager.
 */
public abstract class AbstractNodeManager implements INodeManager {
	
	/** The defined nodes. */
	protected ArrayList<MapNode> definedNodes = new ArrayList<>();
	
	/** The def node by name. */
	protected HashMap<String, MapNode> defNodeByName = new HashMap<>();
	
	/** The pending. */
	protected HashBiMap<Short, String> pending = HashBiMap.create();

	/** The Constant MAPNODE_UNKNOWN. */
	public static final MapNode MAPNODE_UNKNOWN = new MapNode("unknown.png", //$NON-NLS-1$
			"sys:unknown", Messages.getString("AbstractNodeManager.DESC_UNKNOWN_NODE"), 1); //$NON-NLS-1$ //$NON-NLS-2$
	static {
		MAPNODE_UNKNOWN.setNodeId((short) -1);
	}
	
	{
		this.definedNodes.add(this.MAPNODE_UNKNOWN);
	}

	/* (non-Javadoc)
	 * @see net.mosstest.servercore.INodeManager#getNode(short)
	 */
	@Override
	public abstract MapNode getNode(short nodeId);

	/* (non-Javadoc)
	 * @see net.mosstest.servercore.INodeManager#putNode(net.mosstest.scripting.MapNode)
	 */
	@Override
	public abstract short putNode(MapNode node) throws MossWorldLoadException;

	/* (non-Javadoc)
	 * @see net.mosstest.servercore.INodeManager#putNodeAlias(java.lang.String, java.lang.String)
	 */
	@Override
	public abstract void putNodeAlias(String alias, String dst);

	/* (non-Javadoc)
	 * @see net.mosstest.servercore.INodeManager#getNode(java.lang.String, boolean)
	 */
	@Override
	public abstract MapNode getNode(String string, boolean isModified);

	/* (non-Javadoc)
	 * @see net.mosstest.servercore.INodeManager#getNode(java.lang.String)
	 */
	@Override
	public abstract MapNode getNode(String string);

	/* (non-Javadoc)
	 * @see net.mosstest.servercore.INodeManager#init()
	 */
	@Override
	public abstract void init();

}
