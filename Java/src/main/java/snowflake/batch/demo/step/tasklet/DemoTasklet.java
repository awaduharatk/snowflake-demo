package snowflake.batch.demo.step.tasklet;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import snowflake.batch.demo.config.SnowFlakeProperties;

@Component
@StepScope
public class DemoTasklet implements Tasklet {

    @Autowired
    private SnowFlakeProperties propertie;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {
        System.out.println("Hello, World!");

        run();
        return RepeatStatus.FINISHED;
    }

    private void run() throws Exception {
        // get connection
        System.out.println("Create JDBC connection");
        Connection connection = getConnection();
        System.out.println("Done creating JDBC connection\n");

        // create statement
        System.out.println("Create JDBC statement");
        Statement statement = connection.createStatement();
        System.out.println("Done creating JDBC statement\n");

        // create a table
        System.out.println("Create demo table");
        statement.executeUpdate("create or replace table demo(c1 string)");
        System.out.println("Done creating demo table\n");

        // insert a row
        System.out.println("Insert 'hello world'");
        statement.executeUpdate("insert into demo values ('hello world')");
        System.out.println("Done inserting 'hello world'\n");

        // query the data
        System.out.println("Query demo");
        // ResultSet resultSet = statement.executeQuery("select * from TEST limit 1;");
        ResultSet resultSet = statement.executeQuery("select * from demo limit 1;");
        System.out.println("Metadata:");
        System.out.println("================================");

        // fetch metadata
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        System.out.println("Number of columns=" + resultSetMetaData.getColumnCount());
        for (int colIdx = 0; colIdx < resultSetMetaData.getColumnCount(); colIdx++) {
            System.out.println(
                "Column " + colIdx + ": type=" + resultSetMetaData.getColumnTypeName(colIdx + 1));
        }

        // fetch data
        System.out.println("\nData:");
        System.out.println("================================");
        int rowIdx = 0;
        while (resultSet.next()) {
            System.out.println("row " + rowIdx + ", column 0: " + resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        connection.close();


    }


    private Connection getConnection() throws SQLException {

        // build connection properties
        Properties properties = new Properties();
        properties.put("user", propertie.getUser()); // replace "" with your user name
        properties.put("password", propertie.getPass()); // replace "" with your password
        properties.put("warehouse",
            propertie.getWarehouse()); // replace "" with target warehouse name
        properties.put("db", propertie.getDb()); // replace "" with target database name
        properties.put("schema", propertie.getSchema()); // replace "" with target schema name
        // properties.put("tracing", "all"); // optional tracing property

        // Replace <account_identifier> with your account identifier. See
        // https://docs.snowflake.com/en/user-guide/admin-account-identifier.html
        // for details.
        String connectStr = "jdbc:snowflake://" + propertie.getUrl();
        return DriverManager.getConnection(connectStr, properties);
    }

}
