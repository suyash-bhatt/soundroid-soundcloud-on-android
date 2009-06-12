package org.soundroid.xml.converters;

import org.soundroid.xml.models.Track;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

public class TrackConverter extends AbstractReflectionConverter {
	
	public TrackConverter(Mapper mapper) {
		super(mapper, new PureJavaReflectionProvider());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return super.unmarshal(reader, context);
	}

	public boolean canConvert(Class type) {				
		return Track.class.isAssignableFrom(type);
	}

}
