package xenoteo.com.github.server;

import io.grpc.stub.StreamObserver;
import xenoteo.com.github.gen.MunicipalOfficeGrpc;
import xenoteo.com.github.gen.MunicipalOfficeOuterClass;

public class MunicipalOffice extends MunicipalOfficeGrpc.MunicipalOfficeImplBase {

    @Override
    public void commission(MunicipalOfficeOuterClass.IssueArguments request,
                           StreamObserver<MunicipalOfficeOuterClass.IssueAnswer> responseObserver) {
        if (request == null){
            System.out.println("null request received.");
            return;
        }

        MunicipalOfficeOuterClass.IssueType issueType = request.getType();
        String issueOwner = request.getName();

        if (issueType == MunicipalOfficeOuterClass.IssueType.UNRECOGNIZED){
            System.out.println("bad issue type provided.");
            return;
        }

        System.out.printf("preparing %s for %s...\n", issueString(issueType), issueOwner);
        handleIssue(issueType);
        System.out.printf("%s for %s is handled\n", issueString(issueType), issueOwner);

        MunicipalOfficeOuterClass.IssueAnswer.Builder response = MunicipalOfficeOuterClass.IssueAnswer.newBuilder();
        response.setAnswer(String.format("%s for %s is handled.\n", issueString(issueType), issueOwner));

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    private String issueString(MunicipalOfficeOuterClass.IssueType issueType) {
        switch (issueType) {
            case ISSUE_TYPE_PASSPORT: return "passport issue";
            case ISSUE_TYPE_CITIZENSHIP: return "citizenship issue";
            case ISSUE_TYPE_RESIDENCE: return "residence issue";
            default: return "unknown issue";
        }
    }

    private void handleIssue(MunicipalOfficeOuterClass.IssueType issueType) {
        int issueTime = getIssueTime(issueType);
        try {
            Thread.sleep(issueTime * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getIssueTime(MunicipalOfficeOuterClass.IssueType issueType){
        switch (issueType) {
            case ISSUE_TYPE_PASSPORT: return 2;
            case ISSUE_TYPE_CITIZENSHIP: return 6;
            case ISSUE_TYPE_RESIDENCE: return 4;
            default: return 0;
        }
    }
}
