DESCRIPTION = "Routing protocol daemon for multi-hop ad-hoc mesh networks."
SECTION = "console/network"
PRIORITY = "optional"
LICENSE = "GPL"
HOMEPAGE = "http://www.open-mesh.net/"

INC_PR = "r1"

RDEPENDS = "kernel-module-tun"

SRC_URI = "http://downloads.open-mesh.net/batman/stable/sources/batman/batman-${PV}.tar.gz \
           file://makefile-fix.patch;patch=1 \
          "

S = "${WORKDIR}/batman-${PV}"

do_compile() {
  oe_runmake
}

do_stage() {
  :
}

do_install() {
  install -d ${D}${bindir}
  install -m 0755 batmand ${D}${bindir}
}
