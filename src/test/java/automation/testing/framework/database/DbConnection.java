package automation.testing.framework.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.bson.Document;
import org.bson.conversions.Bson;

import static org.junit.Assert.assertNotNull;

public class DbConnection {

    private MongoClient mongoClient;
    private MongoDatabase db;
    private Document record;
    private MongoCollection<Document> mongoCollection;

    public void initDatabase(String host, int port, String dbname) {
        if (mongoClient == null) {
            mongoClient = new MongoClient(host, port);
        }
        if (db == null) {
            db = mongoClient.getDatabase(dbname);
        }
    }

    public void resetCollection(String collectionName) {
        db.getCollection(collectionName).deleteMany(Document.parse("{}"));
    }

    @Given("MongoDB is available")
    public void mongodbIsAvailable() {
        initDatabase("localhost", 27017, "db_name");
    }

    @And("the (.*) database collection is empty")
    public void theDatabaseCollectionIsEmpty(String collectionName) {
        resetCollection(collectionName);
    }

    @And("a new record is found with (.*) in the (.*) database collection")
    public void aNewRecordIsFoundWithInTheDatabaseCollection(String email, String collectionName) {
        Bson expectedEmail = Filters.eq("emailAddress", email);
        mongoCollection = db.getCollection(collectionName);
        record = mongoCollection.find(Filters.and(expectedEmail)).first();
        assertNotNull(record);
    }

    @And("a new account created with (.*),(.*),(.*),(.*),(.*) in (.*) database collection")
    public void aNewAccountCreatedWithInDatabaseCollection(
            String email,
            String name,
            String dob,
            String mobile,
            String lang,
            String collectionName) {
        Bson expectedEmail = Filters.eq("email_address", email);
        Bson expectedName = Filters.eq("name", name);
        Bson expectedDOB = Filters.eq("date_of_birth", dob);
        Bson expectedMobile = Filters.eq("mobile_number", mobile);
        Bson expectedLang = Filters.eq("language", lang);
        mongoCollection = db.getCollection(collectionName);
        record =
                mongoCollection
                        .find(
                                Filters.and(
                                        expectedEmail,
                                        expectedName,
                                        expectedDOB,
                                        expectedMobile,
                                        expectedLang))
                        .first();
    }

    @And("password is created or updated in the database")
    public void passwordIsCreatedOrUpdatedInDatabse() {
        mongoCollection = db.getCollection("account");
        String password = mongoCollection.find().first().get("auth").toString();
        assertNotNull(password);
    }

    public String databaseRecordId() {
        mongoCollection = db.getCollection("account");
        return mongoCollection.find().first().get("_id").toString();
    }
}
