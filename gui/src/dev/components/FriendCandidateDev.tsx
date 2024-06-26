import {useMyInfo} from "@/hooks/useMyInfo.ts";
import {AccountCandidate} from "@/components/account/AccountCandidate.tsx";

export function FriendCandidateDev() {
  const {myInfo} = useMyInfo();

  return (
    <>
      {myInfo && (
        <AccountCandidate account={myInfo} onSubmit={() => {}} />
      )}
    </>
  )
}
