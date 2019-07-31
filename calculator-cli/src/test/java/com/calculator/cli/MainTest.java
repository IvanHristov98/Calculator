package com.calculator.cli;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.inOrder;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;

public class MainTest {
	@Mock
	Launcher launcher;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void verifyLauncherRunExecution_main() throws Exception {
		Main.setLauncher(this.launcher);

		Main.main(new String[] { "1" });

		InOrder mockOrder = inOrder(this.launcher);

		mockOrder.verify(this.launcher).run(any());
		mockOrder.verifyNoMoreInteractions();
	}
}
