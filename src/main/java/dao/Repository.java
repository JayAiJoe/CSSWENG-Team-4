package dao;

import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
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

    public boolean addEmployee(EmployeePOJO employee) {
        MongoCollection<EmployeePOJO> collection = database.getCollection("employees", EmployeePOJO.class);
        InsertOneResult result = collection.insertOne(employee);

        return result.wasAcknowledged();
    }

    public boolean addLogBook(ArrayList<LogbookPOJO> attendance) {
        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);
        InsertManyResult result = collection.insertMany(attendance);

        return result.wasAcknowledged();
    }

    public boolean addDebt(DebtPOJO debt) {
        MongoCollection<DebtPOJO> collection = database.getCollection("debt", DebtPOJO.class);
        InsertOneResult result = collection.insertOne(debt);

        return result.wasAcknowledged();
    }

    public boolean addPerformance(PerformancePOJO performance) {
        MongoCollection<PerformancePOJO> collection = database.getCollection("performance", PerformancePOJO.class);
        InsertOneResult result = collection.insertOne(performance);

        return result.wasAcknowledged();
    }

    public boolean addWorkday(WorkdayPOJO workday) {
        MongoCollection<WorkdayPOJO> collection = database.getCollection("workday", WorkdayPOJO.class);
        InsertOneResult result = collection.insertOne(workday);

        return result.wasAcknowledged();
    }

    public boolean addCola(ColaPOJO cola) {
        MongoCollection<ColaPOJO> collection = database.getCollection("cola", ColaPOJO.class);
        InsertOneResult result = collection.insertOne(cola);

        return result.wasAcknowledged();
    }

    public EmployeePOJO findEmployee(int employeeID) {
        MongoCollection<EmployeePOJO> collection = database.getCollection("employees", EmployeePOJO.class);

        return collection.find(eq("employeeID", employeeID)).first();
    }

    public EmployeePOJO findEmployee(String lowerCaseName) {
        MongoCollection<EmployeePOJO> collection = database.getCollection("employees", EmployeePOJO.class);

        return collection.find(eq("lowerCaseName", lowerCaseName)).first();
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
        performance = collection.find(and(eq("employeeID", employeeID), eq("dateStart", dateStart))).first();

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

    public void deleteLogbook(Date startDate, Date endDate) {
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

    public ArrayList<LogbookPOJO> getPendingOT(Date startDate, Date endDate) {
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

    public ArrayList<ColaPOJO> getCola(Date startDate, Date endDate) {
        MongoCollection<ColaPOJO> collection = database.getCollection("cola", ColaPOJO.class);
        ArrayList<ColaPOJO> cola = new ArrayList<>();
        if (endDate == null) {
            collection.find(gte("date", startDate)).into(cola);
        } else if (startDate == null) {
            collection.find(lte("date", endDate)).into(cola);
        } else {
            collection.find(and(gte("date", startDate), lte("date", endDate))).into(cola);
        }

        return cola;
    }

    public ArrayList<ColaPOJO> getEmployeeCola(int employeeID, Date startDate, Date endDate) {
        MongoCollection<ColaPOJO> collection = database.getCollection("cola", ColaPOJO.class);
        ArrayList<ColaPOJO> cola = new ArrayList<>();
        if (endDate == null) {
            collection.find(and(gte("date", startDate), eq("employeeID", employeeID))).into(cola);
        } else if (startDate == null) {
            collection.find(and(lte("date", endDate), eq("employeeID", employeeID))).into(cola);
        } else {
            collection.find(and(gte("date", startDate), lte("date", endDate), eq("employeeID", employeeID))).into(cola);
        }

        return cola;
    }

    public void updateLogbookOT(ArrayList<LogbookPOJO> logbook) {
        MongoCollection<LogbookPOJO> collection = database.getCollection("logbook", LogbookPOJO.class);

        for (LogbookPOJO entry : logbook) {
            collection.replaceOne(and(eq("date", entry.getDate()), eq("employeeID", entry.getEmployeeID())), entry);
        }
    }

    public void updateEmployee(EmployeePOJO employee) {
        MongoCollection<EmployeePOJO> collection = database.getCollection("employees", EmployeePOJO.class);

        collection.replaceOne(eq("employeeID", employee.getEmployeeID()), employee);
    }

    public void updateWorkday(WorkdayPOJO workday) {
        MongoCollection<WorkdayPOJO> collection = database.getCollection("workday", WorkdayPOJO.class);

        collection.replaceOne(eq("date", workday.getDate()), workday);
    }

    public void updateCola(ColaPOJO cola) {
        MongoCollection<ColaPOJO> collection = database.getCollection("cola", ColaPOJO.class);

        collection.replaceOne(and(eq("date", cola.getDate()), eq("employeeID", cola.getEmployeeID())), cola);
    }
}