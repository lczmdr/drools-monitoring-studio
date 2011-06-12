package com.lucazamador.drools.monitoring.studio;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.lucazamador.drools.monitoring.core.DroolsMonitoring;
import com.lucazamador.drools.monitoring.core.DroolsMonitoringFactory;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.studio.model.DroolsMonitor;
import com.lucazamador.drools.monitoring.studio.recovery.RecoveryStudioListener;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

    private static DroolsMonitoring droolsMonitoring;
    private static DroolsMonitor droolsMonitor;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
     * IApplicationContext)
     */
    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        try {
            int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
            if (returnCode == PlatformUI.RETURN_RESTART)
                return IApplication.EXIT_RESTART;
            else
                return IApplication.EXIT_OK;
        } finally {
            display.dispose();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    public void stop() {
        if (!PlatformUI.isWorkbenchRunning())
            return;
        final IWorkbench workbench = PlatformUI.getWorkbench();
        final Display display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            public void run() {
                if (!display.isDisposed())
                    workbench.close();
            }
        });
    }

    public static DroolsMonitoring getDroolsMonitoring() {
        if (droolsMonitoring == null) {
            droolsMonitoring = DroolsMonitoringFactory.newDroolsMonitoring();
            droolsMonitoring.registerRecoveryAgentListener(new RecoveryStudioListener(PlatformUI.getWorkbench().getActiveWorkbenchWindow()));
            try {
                droolsMonitoring.start();
            } catch (DroolsMonitoringException e) {
                e.printStackTrace();
            }
        }
        return droolsMonitoring;
    }

    public static DroolsMonitor getDroolsMonitor() {
        if (droolsMonitor == null) {
            droolsMonitor = new DroolsMonitor();
        }
        return droolsMonitor;
    }

}
