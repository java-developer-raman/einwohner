FROM ubuntu:18.10

ARG VAULT_VERSION=1.2.2

RUN apt update && apt -y install wget unzip libcap2-bin \
    && wget https://releases.hashicorp.com/vault/${VAULT_VERSION}/vault_${VAULT_VERSION}_linux_amd64.zip \
    && mv consul /usr/local/bin \
    && unzip vault_${VAULT_VERSION}_linux_amd64.zip \
    && chown root:root vault \
    && mv vault /usr/local/bin \
    && setcap cap_ipc_lock=+ep /usr/local/bin/vault \
    && rm vault_${VAULT_VERSION}_linux_amd64.zip \
    && apt purge wget unzip -y \
    && apt autoremove -y

RUN useradd --system --home /etc/vault.d --shell /bin/false vault && \
    mkdir --parents /etc/vault.d

COPY vault.hcl /etc/vault.d

RUN chown --recursive vault:vault /etc/vault.d && \
    chmod 640 /etc/vault.d/vault.hcl

VOLUME /etc/vault/data
# Expose the logs directory as a volume since there's potentially long-running
# state in there
VOLUME /vault/logs
# Expose the file directory as a volume since there's potentially long-running
# state in there
VOLUME /vault/file

EXPOSE 8200

CMD ["vault", "server", "-config=/etc/vault.d/vault.hcl"]