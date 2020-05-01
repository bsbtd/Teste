# Building Binary Packages

## Prerequisites

- git
- Docker

## Supported Distributions

Currently we build 64bit binary packages for the following distributions:

- Debian 8 (Jessie)
- Debian 9 (Stretch)
- Enterprise Linux 6 (CentOS/RedHat/Oracle/Scientific Linux)
- Enterprise Linux 7 (CentOS/RedHat/Oracle/Scientific Linux)
- Ubuntu 16.04 (Xenial)
- Ubuntu 18.04 (Bionic)

## Building Packages

To build packages for the current API Umbrella version for all distributions:

```sh
$ git clone https://github.com/NREL/api-umbrella.git
$ cd api-umbrella
$ make -C build/package -j4 docker_all # Adjust concurrency with -j flag as desired
```

Packages for each distribution will be created inside an isolated docker container, with the resulting packages being placed in the `build/package/work/current` directory.

## Publishing Packages

To publish the new binary packages to our [BinTray repositories](https://bintray.com/nrel) (which provide yum and apt repos):

```sh
$ BINTRAY_USERNAME=username BINTRAY_API_KEY=api_key GPG_PASSPHRASE=passphrase ./build/package/publish
```
