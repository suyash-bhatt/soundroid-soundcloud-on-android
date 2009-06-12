package org.soundroid.xml.converters;

import org.soundroid.xml.models.Event;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

public class EventConverter extends AbstractReflectionConverter {
	
	public EventConverter(Mapper mapper) {
		super(mapper, new PureJavaReflectionProvider());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return super.unmarshal(reader, context);
	}

	public boolean canConvert(Class type) {				
		return Event.class.isAssignableFrom(type);
	}

}
