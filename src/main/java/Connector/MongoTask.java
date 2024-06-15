package Connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoTask extends SourceTask{
	
	public MongoClient mongoclient;
	private Map<String, String> config;
	private MongoCollection<Document> collection;
	private MongoCursor<Document> c;
	private int i;

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(Map<String, String> props) {
		// TODO Auto-generated method stub
		config = props;
		String URI = config.get("connect.uri");
		mongoclient = MongoClients.create(URI);
		MongoDatabase database = mongoclient.getDatabase(config.get("db.name"));
		collection = database.getCollection(config.get("collection.name"));		
	}

	@Override
	public List<SourceRecord> poll() throws InterruptedException {
		
		List <SourceRecord> listofrecords = new ArrayList<>();
		c = collection.find().skip(i).limit(10).cursor();
		while (c.hasNext() ) {
			 Document v = c.next();
			SourceRecord record = new SourceRecord(null,null,config.get("topic.name"),Schema.STRING_SCHEMA,v.getObjectId("_id").toString(),Schema.STRING_SCHEMA,v.toString());
			System.out.println(record.value());
			listofrecords.add(record);
		}
		i +=10;
		return listofrecords;
	}

	@Override
	public void stop() { 
		mongoclient.close();
	}

}
