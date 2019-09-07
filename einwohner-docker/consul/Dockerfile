FROM ubuntu:18.10

ARG CONSUL_VERSION=1.5.3

RUN apt update && apt -y install wget unzip libcap2-bin \
    && wget https://releases.hashicorp.com/consul/${CONSUL_VERSION}/consul_${CONSUL_VERSION}_linux_amd64.zip \
    && unzip consul_${CONSUL_VERSION}_linux_amd64.zip \
    && mv consul /usr/local/bin \
    && rm consul_${CONSUL_VERSION}_linux_amd64.zip \
    && chown root:root vault \
    && mv vault /usr/local/bin \
    && setcap cap_ipc_lock=+ep /usr/local/bin/vault \
    && apt purge wget unzip -y \
    && apt autoremove -y

RUN useradd --system --home /etc/consul.d --shell /bin/false consul && \
    mkdir --parents /opt/consul && \
    chown --recursive consul:consul /opt/consul

COPY consul.service /etc/systemd/system

RUN mkdir --parents /etc/consul.d

COPY consul.hcl /etc/consul.d
COPY server.hcl /etc/consul.d

RUN chown --recursive consul:consul /etc/consul.d && \
    chmod 640 /etc/consul.d/consul.hcl && \
    chmod 640 /etc/consul.d/server.hcl
