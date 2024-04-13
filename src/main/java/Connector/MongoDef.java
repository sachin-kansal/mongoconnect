package Connector;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

public class MongoDef extends AbstractConfig{
	
	public static final String TOPIC_NAME = "topic.name";
	public static final String TOPIC_NAME_DOC = "topic.name.doc";
	public static final String CONNECTION_URI = "connect.uri";
	public static final String CONNECTION_URI_DOC = "connection.uri.doc";
	public static final String NAME_ONLY = "name.only";
	
	public static ConfigDef conf() {
		ConfigDef config =  new ConfigDef();
				 config.define(CONNECTION_URI,Type.STRING,Importance.HIGH, null);
				 config.define(NAME_ONLY,Type.BOOLEAN,Importance.LOW, null);
				 config.define(TOPIC_NAME, Type.STRING, Importance.HIGH, TOPIC_NAME_DOC);
		return config;
	}
	
	public MongoDef(ConfigDef config, Map<String, String> parsedConfig) {
		super(config, parsedConfig);}
	
	public MongoDef(Map<String,String> parsedConfig) {
		this(conf(),parsedConfig);
	}
	
	public String ConnectionURIgetter() {return this.getString(CONNECTION_URI);}
	public String TopicNamegetter() {return this.getString(TOPIC_NAME);}
	public String NameOnlygetter() {return this.getString(NAME_ONLY);}
}