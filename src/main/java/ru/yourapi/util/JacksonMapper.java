package ru.yourapi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapper extends ObjectMapper {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 6983970095455278754L;

    /**
     * Private static instance
     */
    private static ObjectMapper INSTANCE = new JacksonMapper();

    /**
     * Private constructor
     */
    private JacksonMapper() {
        // Initialize instance
        this.initializeInstance();
    }

    /**
     * @return the Object Mapper
     */
    public static ObjectMapper getInstance() {
        return INSTANCE;
    }

    /**
     * Initialize the instance
     */
    private void initializeInstance() {
        this.setDefaultTyping(new FilteredTypeResolverBuilder(DefaultTyping.NON_FINAL).init(JsonTypeInfo.Id.MINIMAL_CLASS, null)
                .inclusion(JsonTypeInfo.As.WRAPPER_OBJECT));
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.setConfig(this.getSerializationConfig().withView(JacksonViews.AuthorizationEnablerView.class));
    }

    protected static class FilteredTypeResolverBuilder extends DefaultTypeResolverBuilder {
        /**
         * Serial Version UID
         */
        private static final long serialVersionUID = 1044981283003407056L;

        /**
         * @param defaultTyping with the default typing
         */
        public FilteredTypeResolverBuilder(final DefaultTyping defaultTyping) {
            super(defaultTyping);
        }
    }
}
