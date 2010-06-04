DESCRIPTION = "VuPlus drivers"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"


KV = "2.6.18-7.3"
PV = "2.6.18-7.3"


RDEPENDS = "initscripts-vuplus kernel (${KV})"
PR = "r13"

SRC_URI = "file://dvb-bcm7335.ko \
	file://dvb-core.ko \
	file://fb.ko \
	file://brcmfb.ko \
	file://procmk.ko"


S = "${WORKDIR}"

do_install() {
	install -d ${D}/lib/modules/${KV}/extra
	install -m 0755    ${WORKDIR}/dvb-bcm7335.ko	${D}/lib/modules/${KV}/extra
	install -m 0755    ${WORKDIR}/dvb-core.ko	${D}/lib/modules/${KV}/extra
	install -m 0755    ${WORKDIR}/fb.ko	${D}/lib/modules/${KV}/extra
	install -m 0755    ${WORKDIR}/procmk.ko	${D}/lib/modules/${KV}/extra
	install -m 0755    ${WORKDIR}/brcmfb.ko	${D}/lib/modules/${KV}/extra
}

do_runstrip() {
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES_${PN} = "/"
