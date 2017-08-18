# Sharenotes

# Load testing:

Server had overall better performance with asynchronous methods when scaled compared to the syncronous methods.
Removing blocking tasks from main thread improved response time, transaction rate, # of hits, # of connections when scaled to 255 users.

Below are some tests using Siege load tester displaying results with the max (255) concurrent users. Internet mode is also enabled so real experience is shown.

# Sync API
![sync](Load/sync.png)

# Async API
![async](Load/async.png)
