package Connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.bson.Document;

import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;

public class MongoTask extends SourceTask{
	
	private static MongoClient mongoclient;
	private Map<String, String> config;
	

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(Map<String, String> props) {
		// TODO Auto-generated method stub
		config = props;
		String URI = props.get("connect.uri");
		mongoclient = MongoClients.create(URI);
		
	}

	@Override
	public List<SourceRecord> poll() throws InterruptedException {
		// TODO Auto-generated method stub
		ListDatabasesIterable<Document> a = mongoclient.listDatabases();
		a.nameOnly(Boolean.parseBoolean(config.get("name.only")));
		a.filter(null);
		MongoCursor<Document> b = a.cursor();
		@SuppressWarnings("rawtypes")
		ArrayList <SourceRecord> listofrecords = new ArrayList();
		int i=0;
		while ( b.hasNext() && i<=10) {
			SourceRecord record = new SourceRecord(null, null, config.get("topic.name"), null, b.next());
			listofrecords.add(record);
			i++;
		}
		return listofrecords;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		mongoclient.close();
	}

}
