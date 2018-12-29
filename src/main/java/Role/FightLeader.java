package Role;

import Forms.*;

public interface FightLeader extends Fighter{
    public void SetLeaderground(Coordinate a, Formation form);
    public void GiveOrder(String a, Formation form);
}