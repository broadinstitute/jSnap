package org.broadinstitute.jsnap;

import java.util.List;

/**
 * Created by Osiris on 10/31/2017.
 */

public interface AsyncResponse {
    void processFinish(List<String> output);
}
