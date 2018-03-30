package database;

import Repository.RightsRolesRepository;
import Repository.User.UserRepositoryMySQL;
import model.Role;
import model.User;
import model.builder.UserBuilder;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ADMINISTRATOR;

import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;

public class Bootstrap {


    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepositoryMySQL userRepository;

    public static void main(String[] args) throws SQLException {
        dropAll();
         System.out.println("Done dropping tables");
        bootstrapTables();
        bootstrapUserData();
        bootstrapAdmin();

    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role_right`;",
                    "DROP TABLE `role_right`;",
                    "TRUNCATE `right`;",
                    "TRUNCATE `user_role`;",
                    "DROP TABLE `user_role`;",
                    "TRUNCATE `role`;",
                    "TRUNCATE `account`;",
                    "DROP TABLE `account`",
                    "TRUNCATE `client`",
                    "TRUNCATE `activity`",
                    "DROP TABLE `activity`",
                    "DROP TABLE  `role`, `user`,`client`"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rightsRolesRepository.addRole(role);
        }
    }

    private static void bootstrapRights() throws SQLException {
        for (String right : RIGHTS) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRoleRight() throws SQLException {
        Map<String, List<String>> rolesRights = getRolesRights();

        for (String role : rolesRights.keySet()) {
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for (String right : rolesRights.get(role)) {
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

                rightsRolesRepository.addRoleRight(roleId, rightId);
            }
        }
    }

    private static void bootstrapUserData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepository(connectionWrapper.getConnection());

            bootstrapRoles();
            bootstrapRights();
            bootstrapRoleRight();



        }
    }

    private static void bootstrapAdmin() throws SQLException{
        for (String schema : SCHEMAS) {
            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepository(connectionWrapper.getConnection());
            userRepository=new UserRepositoryMySQL(connectionWrapper.getConnection(),rightsRolesRepository);
            Role customerRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
            User user=new UserBuilder().setUsername("claudiu.detesan")
                    .setPassword(encodePassword("Admininstrator9$"))
                    .setRoles(Collections.singletonList(customerRole))
                    .build();

            userRepository.save(user);
            System.out.println("Added admin in schema:"+schema);


       }
    }

    private static String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }









}
