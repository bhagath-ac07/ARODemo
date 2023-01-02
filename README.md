# ARODemo
oc login <cluster_url>
echo -n '{"auths": {"my-registry.com": {"username": "my-username", "password": "my-password"}}}' | base64
oc apply -f secrets.yaml
