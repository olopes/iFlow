/*
 * <p>Title: NewFeaturesManager.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) Jul 26, 2005</p>
 * <p>Company: iKnow</p>
 * @author Pedro Monteiro
 * @version 1.0
 */

package pt.iflow.features;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import pt.iflow.api.db.DatabaseInterface;
import pt.iflow.api.utils.Logger;
import pt.iflow.api.utils.UserInfoInterface;
import pt.iflow.api.utils.Utils;

public class NewFeaturesManager {

  public NewFeaturesManager() {
  }

  public static boolean insert(UserInfoInterface userInfo, NewFeaturesData nfFeature) {
    String userid = userInfo.getUtilizador();
    DataSource ds = null;
    Connection db = null;
    PreparedStatement pst = null;

    boolean retObj = false;

    try {
      ds = Utils.getDataSource();
      db = ds.getConnection();
      db.setAutoCommit(true);
      
      StringBuffer sbInsertQuery = new StringBuffer();

      sbInsertQuery.append("insert into new_features (newfeaturesid, version, feature, ");
      sbInsertQuery.append("description, created) values (seq_new_features.nextval,'?");
      sbInsertQuery.append("','?");
      sbInsertQuery.append("','?").append("',");
      if (nfFeature.getCreated() == null) {
        sbInsertQuery.append("sysdate");
      } else {
        sbInsertQuery.append("'?").append("'");
      }
      sbInsertQuery.append(")");

      Logger.debug(userid, NewFeaturesManager.class, "insertNewFeature", "NewFeaturesManager: insert: " + sbInsertQuery.toString());
      pst = db.prepareStatement(sbInsertQuery.toString());
      pst.setString(1, nfFeature.getVersion());
      pst.setString(2, nfFeature.getFeature());
      pst.setString(3, nfFeature.getDescription());
      if (nfFeature.getCreated() != null)
    		  pst.setTimestamp(1, nfFeature.getCreated());
      int i = pst.executeUpdate();
      if (i > 0) {
        retObj = true;
      }

    } catch (SQLException sqle) {
      Logger.error(userid, NewFeaturesManager.class, "insertNewFeature", "caught sql exception: " + sqle.getMessage(), sqle);
    } catch (Exception e) {
      Logger.error(userid, NewFeaturesManager.class, "insertNewFeature", "caught exception: "
          + e.getMessage(), e);
    } finally {
      DatabaseInterface.closeResources(db, pst);
    }

    return retObj;
  }

  public static boolean update(UserInfoInterface userInfo, NewFeaturesData nfa) {
    String userid = userInfo.getUtilizador();
    DataSource ds = null;
    Connection db = null;
    PreparedStatement pst = null;

    boolean retObj = false;

    try {
      ds = Utils.getDataSource();
      db = ds.getConnection();
      db.setAutoCommit(true);
      
      StringBuffer sbtmp = new StringBuffer();

      sbtmp.append("update new_features set version='?");
      sbtmp.append("', feature='?");
      sbtmp.append("', description='?");
      sbtmp.append("', created=sysdate where newfeaturesid=?");

      Logger.debug(userid, NewFeaturesManager.class, "insertNewFeature", "NewFeaturesManager: update: " + sbtmp.toString());
      pst = db.prepareStatement(sbtmp.toString());
      pst.setString(1, nfa.getVersion());
      pst.setString(2, nfa.getFeature());
      pst.setString(3, nfa.getDescription());
      pst.setInt(4, nfa.getId());
      int i = pst.executeUpdate();
      if (i > 0) {
        retObj = true;
      }

    } catch (SQLException sqle) {
      Logger.error(userid, NewFeaturesManager.class, "insertNewFeature",
          "caught sql exception: " + sqle.getMessage(), sqle);
    } catch (Exception e) {
      Logger.error(userid, NewFeaturesManager.class, "insertNewFeature", "caught exception: "
          + e.getMessage(), e);
    } finally {
      DatabaseInterface.closeResources(db, pst);
    }

    return retObj;
  }

  public static boolean delete(UserInfoInterface userInfo, NewFeaturesData nfa) {
    String userid = userInfo.getUtilizador();
    DataSource ds = null;
    Connection db = null;
    Statement st = null;

    boolean retObj = false;

    try {
      ds = Utils.getDataSource();
      db = ds.getConnection();
      db.setAutoCommit(true);
      st = db.createStatement();
      StringBuffer sbtmp = new StringBuffer();

      sbtmp.append("delete from new_features where newfeaturesid=");
      sbtmp.append(nfa.getId());

      Logger.debug(userid, NewFeaturesManager.class, "insertNewFeature", "NewFeaturesManager: delete: " + sbtmp.toString());
      
      int i = st.executeUpdate(sbtmp.toString());
      if (i > 0) {
        retObj = true;
      }

    } catch (SQLException sqle) {
      Logger.error(userid, NewFeaturesManager.class, "insertNewFeature",
          "caught sql exception: " + sqle.getMessage(), sqle);
    } catch (Exception e) {
      Logger.error(userid, NewFeaturesManager.class, "insertNewFeature", "caught exception: "
          + e.getMessage(), e);
    } finally {
      DatabaseInterface.closeResources(db, st);
    }

    return retObj;
  }

  public static Map<String,List<NewFeaturesData>> getNewFeatures(UserInfoInterface userInfo) {
    String userid = userInfo.getUtilizador();
    List<NewFeaturesData> altmp = null;
    Map<String,List<NewFeaturesData>> httmp = new Hashtable<String, List<NewFeaturesData>>();

    DataSource ds = null;
    Connection db = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      ds = Utils.getDataSource();
      db = ds.getConnection();
      st = db.createStatement();
      StringBuffer sbtmp = new StringBuffer();

      sbtmp.append("select * from new_features order by version desc, created desc");
      rs = st.executeQuery(sbtmp.toString());

      while (rs.next()) {
        NewFeaturesData nfatmp = new NewFeaturesData(rs.getInt("newfeaturesid"),
            rs.getString("version"), rs.getString("feature"), 
            rs.getString("description"), rs.getTimestamp("created"));
        if (httmp.containsKey(nfatmp.getVersion())) {
          altmp = httmp.get(nfatmp.getVersion());
        } else {
          altmp = new ArrayList<NewFeaturesData>();
          httmp.put(nfatmp.getVersion(), altmp);
        }
        altmp.add(nfatmp);
        altmp = null;
      }

    } catch (SQLException sqle) {
      Logger.error(userid, NewFeaturesManager.class, "getNewFeatures",
          "caught sql exception: " + sqle.getMessage());
      sqle.printStackTrace();
      httmp = null;
    } catch (Exception e) {
      Logger.error(userid, NewFeaturesManager.class, "getNewFeatures", "caught exception: "
          + e.getMessage());
      e.printStackTrace();
      httmp = null;
    } finally {
      DatabaseInterface.closeResources(db, st, rs);
    }

    return httmp;
  }
}
