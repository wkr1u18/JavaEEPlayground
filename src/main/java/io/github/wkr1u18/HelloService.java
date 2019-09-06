package io.github.wkr1u18;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

class HelloService {
    static final String FALLBACK_NAME = "World";
    static final Lang FALLBACK_LANG = new Lang(1, "Hello", "en");
    private final Logger logger = LoggerFactory.getLogger(HelloService.class);
    private LangRepository repository;

    HelloService() {
        this(new LangRepository());
    }

    HelloService(LangRepository repository) {
        this.repository = repository;
    }

    String prepareGreeting(String name, String lang) {
        Integer langId;
        try {
            langId = Optional.ofNullable(lang).map(Integer::valueOf).orElse(FALLBACK_LANG.getId());
        }
        catch(NumberFormatException nfe) {
            logger.warn("Non-numeric lang used: " + lang);
            langId = FALLBACK_LANG.getId();
        }
        String welcomeMessage = repository.findById(langId).orElse(FALLBACK_LANG).getWelcomeMsg();
        String nameToWelcome = Optional.ofNullable(name).orElse(FALLBACK_NAME);
        return welcomeMessage + " " + nameToWelcome + "!";
    }
}
