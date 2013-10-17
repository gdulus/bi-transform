package pl.burningice.bi.transform.engine

import org.apache.commons.lang.Validate
import pl.burningice.bi.transform.engine.api.Engine

final class EnginesRepository {

    private final Map<Class<? extends Engine>, ? extends Engine> engines = [:]

    EnginesRepository(Engine... engines) {
        engines.each {
            register(it)
        }
    }

    public void register(final Engine engine) {
        Validate.notNull(engine)
        engines[engine.class] = engine
    }

    public Engine get(final Class<? extends Engine> engineType) {
        return engines[engineType]
    }

}
