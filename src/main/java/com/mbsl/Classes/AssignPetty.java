/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

/**
 *
 * @author MBSL2523
 */
import com.mbsl.velocity.as400.environment.AS400Libraries;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignPetty {

    public boolean pettyAssign(String usrcode, String usrccid, String usrnme, String usrccds, String usrsts, String restType) throws SQLException {

        boolean rtnStts = false;

        if (restType.equals("User Grant")) {
            int dataHave = 0;
            String query = new StringBuffer().append("SELECT MUCUSRC, MUCUSCC FROM MPCUC00 WHERE MUCUSRC = '" + usrcode + "' AND MUCUSCC = " + usrccid).toString();
            try (ResultSet rs = AS400DBConnection.search(query)) {
                try {
                    while (rs.next()) {
                        dataHave = 1;
                    }
                } finally {
                    rs.getStatement().close();
                }
            }

            int st2 = 0;

            if (dataHave == 0) {
                int lastId = 0;
                String query1 = new StringBuffer().append("SELECT MSRNO FROM MPCSR00 WHERE MSRCODE = 'USR' ").toString();
                try (ResultSet rs = AS400DBConnection.search(query1)) {
                    try {
                        while (rs.next()) {
                            lastId = rs.getInt(1);
                        }
                    } finally {
                        rs.getStatement().close();
                    }
                }
                lastId += 1;

                String queryAs400Upd1 = new StringBuffer("update MPCSR00 set MSRNO = ").append(lastId)
                        .append(" WHERE MSRCODE = 'USR' ")
                        .toString();

                int st1 = AS400DBConnection.changeData(queryAs400Upd1);

                /**
                 * PETTY CASH NEW USER CREATION *
                 */
                String queryAs400Add = new StringBuffer("insert into MPCUC00 (MUCBK ,MUCUSRC, MUCUSRN, MUCUSCC, MUCCDES, MUCSTAT, MUCSRNO) values (")
                        .append(1)
                        .append(",'").append(usrcode)
                        .append("','").append(usrnme)
                        .append("',").append(usrccid)
                        .append(",'").append(usrccds)
                        .append("',").append(usrsts)
                        .append(",").append(lastId)
                        .append(")")
                        .toString();

                st2 = AS400DBConnection.changeData(queryAs400Add);
                rtnStts = true;
            }

        } else if (restType.equals("User Transfer")) {

            int dataHave = 0;
            String query = new StringBuffer().append("SELECT MUCUSRC, MUCUSCC FROM MPCUC00 WHERE MUCUSRC = '" + usrcode + "' AND MUCUSCC = " + usrccid).toString();
            try (ResultSet rs = AS400DBConnection.search(query)) {
                try {
                    while (rs.next()) {
                        dataHave = 1;
                    }
                } finally {
                    rs.getStatement().close();
                }
            }

            int st2 = 0;

            if (dataHave == 0) {
                int lastId = 0;
                String query1 = new StringBuffer().append("SELECT MSRNO FROM MPCSR00 WHERE MSRCODE = 'USR' ").toString();
                try (ResultSet rs = AS400DBConnection.search(query1)) {
                    try {
                        while (rs.next()) {
                            lastId = rs.getInt(1);
                        }
                    } finally {
                        rs.getStatement().close();
                    }
                }
                lastId += 1;

                String queryAs400Upd1 = new StringBuffer("update MPCSR00 set MSRNO = ").append(lastId)
                        .append(" WHERE MSRCODE = 'USR' ")
                        .toString();

                int st1 = AS400DBConnection.changeData(queryAs400Upd1);

                /**
                 * PETTY CASH NEW USER CREATION *
                 */
                String queryAs400Add = new StringBuffer("insert into MPCUC00 (MUCBK ,MUCUSRC, MUCUSRN, MUCUSCC, MUCCDES, MUCSTAT, MUCSRNO) values (")
                        .append(1)
                        .append(",'").append(usrcode)
                        .append("','").append(usrnme)
                        .append("',").append(usrccid)
                        .append(",'").append(usrccds)
                        .append("',").append(usrsts)
                        .append(",").append(lastId)
                        .append(")")
                        .toString();

                st2 = AS400DBConnection.changeData(queryAs400Add);
                rtnStts = true;
            } else {

                /**
                 * PETTY CASH NEW USER CREATION *
                 */
                String queryAs400Add = new StringBuffer("UPDATE MPCUC00 SET MUCUSCC=").append(usrccid)
                        .append(",MUCCDES='").append(usrccds)
                        .append("' WHERE MUCUSRC='").append(usrcode)
                        .append("'").toString();

                st2 = AS400DBConnection.changeData(queryAs400Add);
                rtnStts = true;
            }

        } else if (restType.equals("User Inactivate")) {

            int st2 = 0;

            String queryAs400Add = new StringBuffer("UPDATE MPCUC00 SET MUCSTAT=").append(usrsts)
                    .append(" WHERE MUCUSRC='").append(usrccds)
                    .append("'").toString();

            st2 = AS400DBConnection.changeData(queryAs400Add);
            rtnStts = true;
        }

        return rtnStts;
    }

}
