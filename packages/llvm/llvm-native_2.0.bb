require llvm.inc

SRC_URI = "http://llvm.org/releases/2.0/llvm-${PV}.tar.gz"


inherit native

S = "${WORKDIR}/llvm-${PV}"

do_stage() {
	install -m 755 ${S}/Release/bin/* ${STAGING_BINDIR_NATIVE}/
}