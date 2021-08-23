package dao;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Date;

public class Repository {

    private final String uri = "mongodb+srv://Spade:hyakujuraijuu@cluster0.2v0fq.mongodb.net/test";
    private final String databaseName = "CSSwengS11Team4";

    private CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    private CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    private MongoClient mongoClient = MongoClients.create(uri);
    private MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);

    private static Repository instance = null;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void addEmployee(EmployeePOJO employee) {

        MongoCollection<EmployeePOJO> collection = database.getCollection("employees", EmployeePOJO.class);
        collection.insertOne(employee);
    }

    public void addLogBook(ArrayList<LogbookPOJO> attendance) {

        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);
        collection.insertMany(attendance);

    }

    public void addDebt(DebtPOJO debt) {

        MongoCollection<DebtPOJO> collection = database.getCollection("debt", DebtPOJO.class);
        collection.insertOne(debt);

    }

    public void addPerformance(PerformancePOJO performance) {

        MongoCollection<PerformancePOJO> collection = database.getCollection("performance", PerformancePOJO.class);
        collection.insertOne(performance);

    }

    public EmployeePOJO findEmployee(int employeeID) {

        MongoCollection<EmployeePOJO> collection = database.getCollection("employees", EmployeePOJO.class);
        EmployeePOJO newEmployee;
        newEmployee = collection.find(eq("employeeID", employeeID)).first();

        return newEmployee;

    }

    public ArrayList<EmployeePOJO> getAllEmployees() {

        MongoCollection<EmployeePOJO> collection = database.getCollection("employees", EmployeePOJO.class);
        ArrayList<EmployeePOJO> employees = new ArrayList<>();
        collection.find().into(employees);

        return employees;

    }

    public ArrayList<PerformancePOJO> findPerformance(int employeeID) {

        MongoCollection<PerformancePOJO> collection = database.getCollection("performance", PerformancePOJO.class);
        ArrayList<PerformancePOJO> performance = new ArrayList<>();
        collection.find(eq("employeeID", employeeID)).into(performance);

        return performance;

    }

    public ArrayList<PerformancePOJO> getAllPerformance() {

        MongoCollection<PerformancePOJO> collection = database.getCollection("performance", PerformancePOJO.class);
        ArrayList<PerformancePOJO> performance = new ArrayList<>();
        collection.find().into(performance);

        return performance;

    }

    public PerformancePOJO findPerformanceOne(int employeeID, Date dateStart) {

        MongoCollection<PerformancePOJO> collection = database.getCollection("performance", PerformancePOJO.class);
        PerformancePOJO performance;
        performance = collection.find(and(eq("employeeID", employeeID), gte("dateStart", dateStart))).first();

        return performance;
    }


    public ArrayList<DebtPOJO> findDebt(int employeeID) {

        MongoCollection<DebtPOJO> collection = database.getCollection("debt", DebtPOJO.class);
        ArrayList<DebtPOJO> debt = new ArrayList<>();
        collection.find(eq("employeeID", employeeID)).into(debt);

        return debt;

    }

    public ArrayList<DebtPOJO> getAllDebt() {

        MongoCollection<DebtPOJO> collection = database.getCollection("debt", DebtPOJO.class);
        ArrayList<DebtPOJO> debt = new ArrayList<>();
        collection.find().into(debt);

        return debt;

    }

    public ArrayList<LogbookPOJO> getLogbook() {

        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);
        ArrayList<LogbookPOJO> attendance = new ArrayList<>();
        collection.find().into(attendance);

        return attendance;

    }

    public ArrayList<LogbookPOJO> getAttendance(Date startDate, Date endDate) {

        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);

        ArrayList<LogbookPOJO> attendance = new ArrayList<>();
        if (endDate == null) {
            collection.find(gte("date", startDate)).into(attendance);
        } else if (startDate == null) {
            collection.find(lte("date", endDate)).into(attendance);
        } else {
            collection.find(and(gte("date", startDate), lte("date", endDate))).into(attendance);
        }

        return attendance;

    }

    public ArrayList<LogbookPOJO> getEmployeeAttendance(int employeeID, Date startDate, Date endDate) {

        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);

        ArrayList<LogbookPOJO> attendance = new ArrayList<>();
        if (endDate == null) {
            collection.find(and(gte("date", startDate), eq("employeeID", employeeID))).into(attendance);
        } else if (startDate == null) {
            collection.find(and(lte("date", endDate), eq("employeeID", employeeID))).into(attendance);
        } else {
            collection.find(and(eq("employeeID", employeeID), gte("date", startDate), lte("date", endDate))).into(attendance);
        }
        return attendance;

    }

    public void deleteLogbook(Date startDate, Date endDate){

        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);

        ArrayList<LogbookPOJO> attendance = new ArrayList<>();
        if (endDate == null) {
            collection.deleteMany(gte("date", startDate));
        } else if (startDate == null) {
            collection.deleteMany(lte("date", endDate));
        } else {
            collection.deleteMany(and(gte("date", startDate), lte("date", endDate)));
        }

    }

    public ArrayList<WorkdayPOJO> getWorkdays(Date startDate, Date endDate) {

        MongoCollection<WorkdayPOJO> collection = database.getCollection("workday", WorkdayPOJO.class);
        ArrayList<WorkdayPOJO> workdays = new ArrayList<>();
        if (endDate == null) {
            collection.find(gte("date", startDate)).into(workdays);
        } else if (startDate == null) {
            collection.find(lte("date", endDate)).into(workdays);
        } else {
            collection.find(and(gte("date", startDate), lte("date", endDate))).into(workdays);
        }
        return workdays;

    }

    public ArrayList<LogbookPOJO> getPendingOT(Date startDate, Date endDate){

        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);
        ArrayList<LogbookPOJO> pendingOT = new ArrayList<>();
        if (endDate == null) {
            collection.find(and(gte("date", startDate), gt("pendingOT", 0))).into(pendingOT);
        } else if (startDate == null) {
            collection.find(and(lte("date", endDate), gt("pendingOT", 0))).into(pendingOT);
        } else {
            collection.find(and(gt("pendingOT", 0), gte("date", startDate), lte("date", endDate))).into(pendingOT);
        }

        return pendingOT;

    }

    public void updateLogbookOT(ArrayList<LogbookPOJO> logbook){
        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);

        for(LogbookPOJO entry: logbook){
            collection.replaceOne(and(eq("date",entry.getDate()),eq("employeeID",entry.getEmployeeID())), entry);
        }
    }


}