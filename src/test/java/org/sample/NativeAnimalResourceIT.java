package org.sample;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeAnimalResourceIT extends AnimalResourceTest {

    // Execute the same tests but in native mode.
}