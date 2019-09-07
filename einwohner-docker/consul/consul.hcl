datacenter = "dc1"
data_dir = "/opt/consul"
encrypt = "Luj2FZWwlt8475wD1WtwUQ=="
performance {
  raft_multiplier = 1
}
primary_datacenter = "dc1"
acl {
  enabled = true
  default_policy = "deny"
  down_policy = "extend-cache"
}
