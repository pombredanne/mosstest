package net.mosstest.scripting;

import static org.fusesource.leveldbjni.JniDBFactory.asString;
import static org.fusesource.leveldbjni.JniDBFactory.bytes;
import static org.fusesource.leveldbjni.JniDBFactory.factory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

// TODO: Auto-generated Javadoc
/**
 * The Class ScriptableDatabase.
 */
public class ScriptableDatabase {

	/** The base dir. */
	File baseDir;
	
	/**
	 * Instantiates a new scriptable database.
	 *
	 * @param baseDir the base dir
	 */
	public ScriptableDatabase(File baseDir) {
		this.baseDir = baseDir;

	}

	/**
	 * Gets the db.
	 *
	 * @param name the name
	 * @return the db
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public DBase getDb(String name) throws IOException {
		if (!name.matches("[a-zA-Z]{1,32}")) { //$NON-NLS-1$
			throw new IllegalArgumentException(Messages.getString("ScriptableDatabase.DB_NAME_INVALID")); //$NON-NLS-1$
		}
		Options options = new Options();
		options.createIfMissing(true);
		return new DBase(factory.open(new File(this.baseDir, "sc_" + name //$NON-NLS-1$
				+ ".db"), options), name); //$NON-NLS-1$

	}

	/**
	 * The Class DBase.
	 */
	public class DBase {
		// this class will contain a database that scripts may access.
		/** The inner db. */
		private final DB innerDb;

		/**
		 * Instantiates a new d base.
		 *
		 * @param innerDb the inner db
		 * @param name the name
		 */
		DBase(DB innerDb, String name) {
			this.innerDb = innerDb;
		}

		/**
		 * Get a piece of string data with a string key, or <code>null</code> if
		 * the datum cannot be found. String keys are exclusive from all other
		 * key types.
		 *
		 * @param key the key
		 * @return A string representing the stored data.
		 */
		public String getDatum(String key) {
			byte[] keyBytes = bytes("string::" + key); //$NON-NLS-1$
			byte[] dataBytes = this.innerDb.get(keyBytes);
			return (dataBytes == null) ? null : asString(dataBytes);
		}

		/**
		 * Put a datum with a generic string key, overwriting as necessary.
		 * 
		 * @param key
		 *            The string key.
		 * @param data
		 *            The data to put.
		 */
		public void putDatum(String key, String data) {
			this.innerDb.put(bytes("string::" + key), bytes(data)); //$NON-NLS-1$
		}

		/**
		 * Get a piece of string data with a position and string key, or
		 * <code>null</code> if the datum cannot be found. Position keys are
		 * exclusive from all other key types.
		 *
		 * @param pos the pos
		 * @param key the key
		 * @return The found data.
		 */
		public String getPositionDatum(NodePosition pos, String key) {
			byte[] keyBytes = ArrayUtils.addAll(bytes("npos::"), //$NON-NLS-1$
					ArrayUtils.addAll(pos.toBytes(), bytes("str::" + key))); //$NON-NLS-1$
			byte[] dataBytes = this.innerDb.get(keyBytes);
			return (dataBytes == null) ? null : asString(dataBytes);
		}

		/**
		 * Put a datum with a position and string key, overwriting as necessary.
		 * 
		 * @param pos
		 *            The position portion of the key.
		 * @param key
		 *            The string key.
		 * @param data
		 *            The data to put.
		 */
		public void putPositionDatum(NodePosition pos, String key, String data) {
			byte[] keyBytes = ArrayUtils.addAll(bytes("npos::"), //$NON-NLS-1$
					ArrayUtils.addAll(pos.toBytes(), bytes("str::" + key))); //$NON-NLS-1$
			byte[] dataBytes = bytes(data);
			this.innerDb.put(keyBytes, dataBytes);
		}

		/**
		 * Gets a string of data associated with a chunk and string key, or
		 * <code>null</code> if not found.
		 * 
		 * @param pos The chunk position.
		 * @param key The string key.
		 * @return The found data.
		 */
		public String getChunkDatum(Position pos, String key) {
			byte[] keyBytes = ArrayUtils.addAll(bytes("cpos::"), //$NON-NLS-1$
					ArrayUtils.addAll(pos.toBytes(), bytes("str::" + key))); //$NON-NLS-1$
			byte[] dataBytes = this.innerDb.get(keyBytes);
			return (dataBytes == null) ? null : asString(dataBytes);
		}

		/**
		 * Puts a string of data keyed with a string and chunk.
		 * @param pos The chunk position.
		 * @param key The string key.
		 * @param data The data to put.
		 */
		public void putChunkDatum(Position pos, String key, String data) {
			byte[] keyBytes = ArrayUtils.addAll(bytes("cpos::"), //$NON-NLS-1$
					ArrayUtils.addAll(pos.toBytes(), bytes("str::" + key))); //$NON-NLS-1$
			byte[] dataBytes = bytes(data);
			this.innerDb.put(keyBytes, dataBytes);
		}
		/**
		 * Gets a string of data associated with a player and string key, or
		 * <code>null</code> if not found.
		 * 
		 * @param p The player.
		 * @param key The string key.
		 * @return The found data.
		 */
		public String getPlayerDatum(Player p, String key) {
			byte[] keyBytes = bytes("plr::" + p.name + "str::" + key); //$NON-NLS-1$ //$NON-NLS-2$
			byte[] dataBytes = this.innerDb.get(keyBytes);
			return (dataBytes == null) ? null : asString(dataBytes);
		}
		/**
		 * Puts a string of data keyed with a string and player.
		 * @param p The player.
		 * @param key The string key.
		 * @param data The data to put.
		 */
		public void putPlayerDatum(Player p, String key, String data) {
			byte[] keyBytes = bytes("plr::" + p.name + "str::" + key); //$NON-NLS-1$ //$NON-NLS-2$
			byte[] dataBytes = bytes(data);
			this.innerDb.put(keyBytes, dataBytes);
		}

	}
}
