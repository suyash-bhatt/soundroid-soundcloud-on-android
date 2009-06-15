package org.soundroid.xml.converters;



import org.soundroid.xml.models.Users;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

public class UsersConverter extends CollectionConverter {

	public UsersConverter(Mapper mapper) {
		super(mapper);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return super.unmarshal(reader, context);
	}

	public boolean canConvert(Class type) {
		return Users.class.isAssignableFrom(type);
	}
}

