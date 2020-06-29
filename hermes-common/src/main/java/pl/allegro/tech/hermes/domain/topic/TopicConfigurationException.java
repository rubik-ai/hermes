package pl.allegro.tech.hermes.domain.topic;

import pl.allegro.tech.hermes.api.ErrorCode;
import pl.allegro.tech.hermes.common.exception.HermesException;

public class TopicConfigurationException extends HermesException {

    public TopicConfigurationException(Throwable t) {
        super(t.getMessage());
    }

    @Override
    public ErrorCode getCode() {
        return ErrorCode.VALIDATION_ERROR;
    }
}
