package org.soundroid.xml.converters;

import org.soundroid.xml.models.User;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

public class UserConverter extends AbstractReflectionConverter {
	
	public UserConverter(Mapper mapper) {
		super(mapper, new PureJavaReflectionProvider());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return super.unmarshal(reader, context);
	}

	public boolean canConvert(Class type) {				
		return User.class.isAssignableFrom(type);
	}
}
