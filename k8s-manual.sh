#need to update
docker build -t b2b/customer-service:1.0 .
docker tag b2b/customer-service:1.0 apphub.rvbb.vn/b2b/customer-service:1.0
docker push apphub.rvbb.vn/b2b2/customer-service:1.0
