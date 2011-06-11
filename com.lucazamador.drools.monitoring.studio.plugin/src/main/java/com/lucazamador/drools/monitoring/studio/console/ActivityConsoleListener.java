package com.lucazamador.drools.monitoring.studio.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionMetric;

public class ActivityConsoleListener implements DroolsMonitoringListener {

    private final String consoleViewId;
    private MessageConsoleStream messageConsoleStream;

    public ActivityConsoleListener(String consoleViewId) {
        this.consoleViewId = consoleViewId;
    }

    @Override
    public void newMetric(AbstractMetric metric) {
        if (messageConsoleStream == null) {
            getMessageConsoleStream();
        }
        if (messageConsoleStream != null) {
            if (metric instanceof KnowledgeSessionMetric) {
                KnowledgeSessionMetric ksessionMetric = (KnowledgeSessionMetric) metric;
                if (!messageConsoleStream.isClosed()) {
                    messageConsoleStream.println("average firing time: " + ksessionMetric.getAverageFiringTime()
                            + " total fact count: " + ksessionMetric.getTotalFactCount());
                }
            }
        }
    }

    private void getMessageConsoleStream() {
        IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
        for (int i = 0; i < consoles.length; i++) {
            if (consoles[i].getName().equals(consoleViewId)) {
                MessageConsole messageConsole = (MessageConsole) consoles[i];
                messageConsoleStream = messageConsole.newMessageStream();
                return;
            }
        }
    }

}
