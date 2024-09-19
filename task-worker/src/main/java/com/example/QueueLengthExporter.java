package com.example;

import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;

public class QueueLengthExporter {

    // Create a Gauge metric for queue length
    private static final Gauge queueLengthGauge = Gauge.build()
            .name("queue_length")
            .help("Queue length gauge.")
            //these labels will be used in the prometheus adapter config
            .labelNames("namespace","pod")
            .register();

    public static void main(String[] args) throws Exception {
        // Start HTTP server to expose metrics
        HTTPServer server = new HTTPServer(1234); // Expose metrics on port 1234

        // Simulate updating the queue length metric
        while (true) {
            int queueLength = getQueueLength(); // Replace with your logic to get queue length
            queueLengthGauge.labels(System.getenv("POD_NAMESPACE"), System.getenv("POD_NAME")).set(queueLength);
            Thread.sleep(180000); // Update every 180 seconds
        }
    }

    private static int getQueueLength() {
        // Generate a random number between 1 and 10 (inclusive)
        return (int) (Math.random() * 10) + 1;
    }
}