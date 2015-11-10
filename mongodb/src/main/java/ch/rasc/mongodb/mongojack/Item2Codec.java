package ch.rasc.mongodb.mongojack;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

//http://mongodb.github.io/mongo-java-driver/3.0/driver/reference/crud/#codecregistry
public class Item2Codec implements Codec<Item2> {
	@Override
	public void encode(BsonWriter writer, Item2 value, EncoderContext encoderContext) {
		writer.writeStartDocument();
		if (value.getId() == null) {
			ObjectId objectId = new ObjectId();
			writer.writeObjectId("_id", objectId);
			value.setId(objectId.toHexString());
		}
		else {
			writer.writeObjectId("_id", new ObjectId(value.getId()));
		}
		writer.writeDouble("data", value.getData());
		writer.writeString("name", value.getName());
		writer.writeEndDocument();
	}

	@Override
	public Class<Item2> getEncoderClass() {
		return Item2.class;
	}

	@Override
	public Item2 decode(BsonReader reader, DecoderContext decoderContext) {
		Item2 item = new Item2();
		reader.readStartDocument();
		while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
			String name = reader.readName();
			switch (name) {
			case "_id":
				item.setId(reader.readObjectId().toHexString());
				break;
			case "data":
				item.setData(reader.readDouble());
				break;
			case "name":
				item.setName(reader.readString());
				break;
			}
		}

		reader.readEndDocument();
		return item;
	}
}
