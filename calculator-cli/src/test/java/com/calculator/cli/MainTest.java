package com.calculator.cli;

import org.mockito.*;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.inOrder;
import static org.mockito.ArgumentMatchers.any;

import org.junit.*;

public class MainTest {
	@Mock
	Launcher launcher;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void verifyLauncherRunExecution_main() throws Exception {
		Main.setLauncher(launcher);

		Main.main(new String[] { "1" });

		InOrder mockOrder = inOrder(launcher);

		mockOrder.verify(launcher).run(any());
		mockOrder.verifyNoMoreInteractions();
	}
}
